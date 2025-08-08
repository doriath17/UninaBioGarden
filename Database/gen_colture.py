import csv
import psycopg
from faker import Faker
import random
import datetime

# faker_seed = random.randint(0, 1000)
random.seed(389)
faker_seed = 389
Faker.seed(faker_seed)
print("faker seed: ", faker_seed)
fake = Faker("it_IT")

colture_e_fiori = [
    "Mela",
    "Pera",
    "Insalata",
    "Pomodori",
    "Patata",
    "Zucchine",
    "Legumi",
    "Fragola",
    "Banana",
    "Pesca",
    "Ciliegia",
    "Melone",
    "Anguria",
    "Albicocche",
    "Frutti rossi",
    "Nespole",
    "Fichi",
    "Rucola",
    "Aglio",
    "Asparagi",
    "Carote",
    "Cavolfiore",
    "Cetrioli",
    "Cipolle",
    "Finocchi",
    "Fiori di zucca",
    "Lattuga",
    "Melanzane",
    "Peperoni",
    "Piselli",
    "Sedano",
    "Spinaci",
    "Zucca",
    "Lillà",
    "Lavanda",
    "Glicine",
    "Orchidea",
    "Viola",
    "Aster",
    "Rose",
    "Petunia",
    "Gerbera",
    "Garofano",
    "Anemone",
    "Papavero",
    "Iris",
    "Fiordaliso",
    "Giglio",
    "Girasole",
    "Gelsomino",
    "Margherita",
    "Narciso",
    "Tulipano",
    "Ortensia"
]

def gen_nome():
  return random.choice(colture_e_fiori)

inizio_progetto = datetime.date(2025, 6, 1)
due_sett_dopo = datetime.date(2025, 6, 15)

def gen_inizio_semina():
  data = fake.date_between(inizio_progetto, due_sett_dopo)
  return data

