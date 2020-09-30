from collections import Counter
from collections import defaultdict
from collections import OrderedDict
from collections import namedtuple
from collections import deque

device_tempratures = [13.5, 14.0, 14.0, 14.5, 14.5, 15.0, 16.0]

temprature_counter = Counter(device_tempratures)
print(temprature_counter[14.0])

##############################################

my_company = "Teclado"

coworkers = ["Jen", "Li", "Charlie"]
other_workers = [("Rolf", "Apple"), ("Anna", "Google")]

coworkers_companies = defaultdict(lambda: my_company)

for person, company in other_workers:
    coworkers_companies[person] = company

print(coworkers_companies[coworkers[0]])
print(coworkers_companies["Rolf"])

#################################################

o = OrderedDict()
o["Rolf"] = 6
o["Jose"] = 12
o.pop("Rolf")
# o.move_to_end("Rolf")
# o.move_to_end("Rolf", last=False)
print(o)

##################################################

account = ("checking", 1850.90)
Account = namedtuple("Account", ["name", "balance"])
account = Account("checking", 1850.90)
print(account)

##################################################

friends = deque(("Rolf", "Charlie", "Anna", "Jen"))
friends.append("Jose")
friends.appendleft("Rajat")
friends.pop()
friends.popleft()
print(friends)
