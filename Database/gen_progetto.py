import psycopg
from faker import Faker
import random
from sql_values import *

random.seed(389)
faker_seed = 389
Faker.seed(faker_seed)
print("faker seed: ", faker_seed)
fake = Faker("it_IT")

prefissi = ["Agro", "Eco", "Bio", "Terra", "Verde", "Radice", "Seme", "Genesi", "Sistemi", "Hub", "Progetto", "Soluzioni"]
termini_casuali = ["Crescita", "Fioritura", "Raccolta", "Futura", "Innovazione", "Natura", "Botanico"]

def gen_nome():
  scelta = random.choice([1, 2])
  if scelta == 1:
    return f"{random.choice(prefissi)} {fake.word().capitalize()}"
  elif scelta == 2:
    return  f"{random.choice(termini_casuali)} {fake.word().capitalize()}"

def gen_data_inizio():
  return fake.date_this_year()

def gen_data_fine(data_inizio):
  if random.randint(0, 100) >= 75 :
    return None
  return fake.past_date(data_inizio)

def gen_descrizione():
  if random.randint(0, 100) >= 30 :
    return None
  return fake.text(max_nb_chars=200).replace('\n', '')

def gen_csv_string(username, id_lotto):
  di = gen_data_inizio()
  df = gen_data_fine(di)
  return f"{sql_string(gen_nome())},{sql_date(di)},{sql_date(df)},{sql_string(gen_descrizione())},{sql_string(username)},{id_lotto}"

column_names = "nome,data_inizio,data_fine,descrizione,username_prop,id_lotto"

def find_usernames():
  with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
    with conn.cursor() as cur:
      sql = "SELECT username FROM proprietario"
      cur.execute(sql)
      result = cur.fetchall()
      usernames = []
      for item in result:
        usernames.append(item[0])
      return usernames

def find_lotti(username):
  with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
    with conn.cursor() as cur:
      sql = f"SELECT id_lotto FROM lotto WHERE username_prop='{username}'"
      cur.execute(sql)
      ids = []
      for lotto in cur.fetchall():
        ids.append(lotto[0])
      return ids

def gen_csv_progetti():
  usernames = find_usernames()
  lines = []
  for username in usernames:
    ids_lotti = find_lotti(username)
    while len(ids_lotti) > 0:
      i = random.randint(0, len(ids_lotti)-1)
      lines.append(gen_csv_string(username, ids_lotti[i]) + '\n')
      ids_lotti.pop(i)
    
  with open("progetti.csv", mode="w") as csv_file:
    csv_file.write(column_names + "\n")
    csv_file.writelines(lines)
    
# gen_csv_progetti()

# print(find_usernames())

def load_progetti():
  with open("progetti.csv", mode="r") as csv_file:
    reader = csv_file.readlines()
    reader.pop(0)
    sql = sql_insert(table="progetto", columns=column_names, values=reader)
    with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
      with conn.cursor() as cur:
        cur.execute(sql)

load_progetti()



# print(find_lotti("proprietario1"))
