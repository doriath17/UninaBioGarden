
class logger:

  def __init__(self):
    self.lines = []

  def log(self, line: str):
    self.lines.append(line)

  def print_log(self):
    for line in self.lines:
      print(line)

logger1 = logger()

logger1.log("Hi")
logger1.log("Hello")
logger1.log("Bye")
logger1.print_log()

logger2 = logger()

logger2.log("Hsghwrth")
logger2.log("Hellorwrth")
logger2.log("Byewrthtrh")
logger2.print_log()
