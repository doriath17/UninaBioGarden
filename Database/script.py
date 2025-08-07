import csv
import psycopg
from faker import Faker
import random

fake = Faker("it_IT")
faker_seed = random.randint(0, 1000)
print("faker seed: ", faker_seed)
Faker.seed(faker_seed)

def load_proprietari():
  with open('proprietario.csv') as csv_file: 
    reader = csv.DictReader(csv_file)
    with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
      with conn.cursor() as cur:
        sql_values = '( VALUES\n'
        for row in reader:
          sql_values += (f"('{row['username']}', '{row['password']}', '{row['nome']}', '{row['cognome']}', '{row['bday']}', '{row['email']}', '{row['nazionalita']}', '{row['num_tel']}', '{row['residenza']}'),\n")

        sql_values = sql_values[:-2]

        sql = "INSERT INTO proprietario (username,password,nome,cognome,bday,email,nazionalita,num_tel,residenza)\n" + sql_values + ' )'

        cur.execute(sql)

def delete_proprietario(username):
  with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
    with conn.cursor() as cur:
      cur.execute(f"DELETE FROM Proprietario WHERE username='{username}'")

def load_coltivatori():
  with open('coltivatore.csv') as csv_file: 
    reader = csv.DictReader(csv_file)
    with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
      with conn.cursor() as cur:
        sql_values = '( VALUES\n'
        for row in reader:
          sql_values += (f"('{row['username']}', '{row['password']}', '{row['nome']}', '{row['cognome']}', '{row['bday']}', '{row['email']}', '{row['nazionalita']}', '{row['num_tel']}', '{row['residenza']}'),\n")

        sql_values = sql_values[:-2]

        sql = "INSERT INTO coltivatore (username,password,nome,cognome,bday,email,nazionalita,num_tel,residenza)\n" + sql_values + ' )'

        cur.execute(sql)
