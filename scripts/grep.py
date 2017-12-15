#!/usr/bin/python

import re
import sys

for line in sys.stdin:
    if re.search(r'PENALTY', line) is not None:
    	gid = line.split(",")
        sys.stdout.write(gid[0])