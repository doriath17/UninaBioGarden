from datetime import date

def sql_string(s: str):
  if s == None:
    return "NULL"
  return f"'{s.replace("'","''")}'"

def sql_date(d: date):
  if d == None:
    return "NULL"
  return f"'{d}'"

def sql_insert(table, columns, values):
  s = f"INSERT INTO {table} ({columns}) VALUES\n"
  for value in values:
    s += f"({value.strip()}),\n"
  return s[:-2]