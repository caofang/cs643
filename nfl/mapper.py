#!/usr/bin/env python
import sys
import re
 
#--- get all lines from stdin ---
for line in sys.stdin:
    #--- remove leading and trailing whitespace---
    line = line.strip()

    if re.search(r'incomplete pass', line) is not None:
    	gid = line.split(",")
        print '%s,%s' % (gid[0], "1")