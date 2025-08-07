import csv
import psycopg
from faker import Faker
import random

# faker_seed = random.randint(0, 1000)
random.seed(389)
faker_seed = 389
Faker.seed(faker_seed)
print("faker seed: ", faker_seed)
fake = Faker("it_IT")

nomi_orto = ["Orto", "Aiuola", "Parco", "Orto Botanico", "Boschetto"]

aggettivi_orto = ["Nascosto", "Incantato", "Luminoso", "Mistico"]


def init_address():
  street_name = fake.street_name()
  city = fake.city()
  postcode = fake.postcode()
  return  f"\"{street_name}, {city}, {postcode}\""

def gen_address():
  address = init_address()
  while len(address) >= 80:
    address = init_address()
  return address

def gen_codice_lotto():
  return random.randint(1, 100)

def gen_estensione_lotto():
  return (random.randint(10, 100) + random.randint(1, 100)/100)

def init_nome_orto():
  if (random.randint(0, 100) <= 70):
    city = fake.city()
    return f"\"{city} {random.choice(nomi_orto)}\""
  else:
    city = fake.city()
    return f"\"{city} {random.choice(aggettivi_orto)} {random.choice(nomi_orto)}\""

def gen_nome_orto():
  orto = init_nome_orto()
  while len(orto) >= 80:
    orto = init_nome_orto()
  return orto

def gen_lotto_csv_string(indirizzo, codice_lotto, estensione, nome_orto, username):
  s = f"{indirizzo},{codice_lotto},{estensione},{nome_orto},{username}\n"
  s = s.replace("'", "")
  return s

class lotto_natual_key:

  def __init__(self, indirizzo, codice):
    self.indirizzo = indirizzo
    self.codice = codice

  def __eq__(self, other):
    if self.indirizzo == other.indirizzo and self.codice == other.codice:
      return True
    return False
  
  def __hash__(self):
    return hash((self.indirizzo, self.codice))

def gen_csv_file(username, rows, mode="a"):
  username = "\"" + username + "\""
  natual_keys = set()
  while len(natual_keys) < rows:
    natual_keys.add(lotto_natual_key(gen_address(), gen_codice_lotto()))
  natual_keys = list(natual_keys)

  csv_lines = []
  for i in range(rows):
    csv_lines.append(gen_lotto_csv_string(
      natual_keys[i].indirizzo,
      natual_keys[i].codice,
      gen_estensione_lotto(),
      gen_nome_orto(),
      username
    ))

  with open("lotti.csv", mode) as csv_file:
    if mode == "w":
      csv_file.write("indirizzo,codice_lotto,estensione,nome_orto,username_prop\n")
    csv_file.writelines(csv_lines)

def get_values_string(indirizzo, codice, est, orto, prop):
  s = f"({indirizzo}, {codice}, {est}, {orto}, {prop}),\n"
  return s

def load_lotti():
  with open("lotti.csv", "r") as csv_file:
    reader = csv.DictReader(csv_file, quotechar="\"")
    with psycopg.connect("dbname=uninabiogarden user=ubg_user") as conn:
      with conn.cursor() as cur:
        sql = "INSERT INTO lotto (indirizzo, codice_lotto, estensione, nome_orto, username_prop)\nVALUES\n"
        for row in reader:
          sql += get_values_string(
            "'"+row['indirizzo']+"'",
            row['codice_lotto'],
            row['estensione'],
            "'"+row['nome_orto']+"'",
            "'"+row['username_prop']+"'"
          )
        sql = sql[:-2]
        cur.execute(sql)

