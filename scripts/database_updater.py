#!/usr/bin/env python

import sys
import os
import hashlib
import sqlite3
import argparse
import csv


BUF_SIZE = 65536  # lets read stuff in 64kb chunks!


def get_hash(input_file: str) -> str:

    sha256 = hashlib.sha256()

    with open(input_file, 'rb') as f:
        while True:
            data = f.read(BUF_SIZE)
            if not data:
                break
            sha256.update(data)

    return "{0}".format(sha256.hexdigest())


def process_csv(input_file: str, database_path: str):

    input_base_path = os.path.basename(input_file)
    input_hash = get_hash(input_file)

    try:
        sqliteConnection = sqlite3.connect(database_path)
        cursor = sqliteConnection.cursor()
        print("Database created and Successfully Connected to SQLite")

        insert_query = """INSERT INTO hills_table (
        number, name, parent_num, section, region, area, island, topoSection, county, classification, metres, feet, climbed, country, hillBaggingUrl, latitude, longitude) 
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """

        #cur.execute("insert into contacts (name, phone, email) values (?, ?, ?)", (name, phone, email))
        with open(input_file, "r") as infile:
            reader = csv.DictReader(infile)

            print("Starting insert of Hills Table")
            for row in reader:
                cursor.execute(
                    insert_query, 
                    (
                        row['Number'],
                        row['Name'], 
                        row['Parent (SMC)'], 
                        row['Section'],
                        row['Region'],
                        row['Area'],
                        row['Island'],
                        row['Topo Section'],
                        row['County'],
                        row['Classification'],
                        row['Metres'],
                        row['Feet'],
                        None, 
                        row['Country'],
                        row['Hill-bagging'],
                        row['Latitude'],
                        row['Longitude']
                    )
                )

        cursor.execute("INSERT INTO hills_meta(csv_name, csv_hash) VALUES(?, ?)", (input_base_path, input_hash))
        sqliteConnection.commit()
                
    except sqlite3.Error as error:
        print("Error while connecting to sqlite", error)
    finally:
        if sqliteConnection:
            sqliteConnection.close()
            print("The SQLite connection is closed")


    


def main():
    parser = argparse.ArgumentParser(prog='database_updater', description='Creates the initial Waundle database')
    parser.add_argument('-i', '--input', help="CSV file to process", required=True)
    parser.add_argument('-d', '--database', help="Database to save into", required=True)


    args = parser.parse_args()
    process_csv(input_file=args.input, database_path=args.database)

if __name__ == "__main__":
    main()
