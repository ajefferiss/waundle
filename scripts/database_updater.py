#!/usr/bin/env python

import argparse
import csv
import hashlib
import os
import sqlite3
from typing import Dict

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


def populate_database(hills_path: str, database_path: str, classifications_path: str):
    try:
        sqlite_conn = sqlite3.connect(database_path)
        print("Database created and Successfully Connected to SQLite")

        classifications = process_classifications_csv(
            classifications_path,
            sqlite_conn
        )

        process_hills_csv(hills_path, sqlite_conn, classifications)

        add_meta_data(hills_path, sqlite_conn)
    except sqlite3.Error as error:
        print("Error while connecting to sqlite", error)
    finally:
        if sqlite_conn:
            sqlite_conn.close()
            print("The SQLite connection is closed")


def add_meta_data(hills_file: str, sql_conn: sqlite3.Connection):
    input_base_path = os.path.basename(hills_file)
    input_hash = get_hash(hills_file)

    cursor = sql_conn.cursor()
    cursor.execute(
        "INSERT INTO hills_meta(csv_name, csv_hash) VALUES(?, ?)",
        (
            input_base_path,
            input_hash
        )
    )
    sql_conn.commit()


def process_hills_csv(hills_file: str, sql_conn: sqlite3.Connection, classifications: Dict[str, int]):
    cursor = sql_conn.cursor()
    hills_query = """INSERT INTO hills_table (
        number, name, parent_num, section, region, area, island, topoSection, county, metres, feet, climbed, country, hillBaggingUrl, latitude, longitude)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
        """
    classification_query = "INSERT INTO hill_classification_lookup(hill_id, classification_id) VALUES (?, ?)"

    with open(hills_file, "r") as infile:
        reader = csv.DictReader(infile)

        print("Starting insert of Hills Table")
        for row in reader:
            cursor.execute(
                hills_query,
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
                    row['Metres'],
                    row['Feet'],
                    None,
                    row['Country'],
                    row['Hill-bagging'],
                    row['Latitude'],
                    row['Longitude']
                )
            )

            hill_classifications = row['Classification'].split(",")
            for classification in hill_classifications:
                row_id = cursor.lastrowid
                classification_id = classifications.get(classification, None)

                if row_id and classification_id:
                    cursor.execute(
                        classification_query,
                        (
                            row_id,
                            classification_id
                        )
                    )

    sql_conn.commit()


def process_classifications_csv(classification_file: str, sql_conn: sqlite3.Connection) -> Dict[str, int]:
    cursor = sql_conn.cursor()
    insert_query = "INSERT INTO hill_classifications(code, name) VALUES (?, ?)"

    with open(classification_file, "r") as infile:
        reader = csv.DictReader(infile)

        print("Starting insert of Hill Classifications")
        for row in reader:
            cursor.execute(insert_query, (row['Code'], row['Name']))

    sql_conn.commit()

    cursor.execute("SELECT id, code FROM hill_classifications")
    return {row[0]: row[1] for row in cursor.fetchall()}


def main():
    parser = argparse.ArgumentParser(prog='database_updater',
                                     description='Creates the initial Waundle database')
    parser.add_argument('-i', '--input',
                        help="CSV file to process",
                        required=True)
    parser.add_argument('-c', '--classifications',
                        help="CSV File containing fill classifications",
                        required=True)
    parser.add_argument('-d', '--database',
                        help="Database to save into",
                        required=True)

    args = parser.parse_args()
    populate_database(hills_path=args.input,
                      database_path=args.database,
                      classifications_path=args.classifications)


if __name__ == "__main__":
    main()
