import re

with open ('day2data.txt', 'r') as file:
    ids = file.read().strip().split(',')

total1 = 0
total2 = 0

factor_cache = {}

def process_range_part1(start, end):
    # calculate number of ids
    cur_total = 0
    for num in range(start, end):
        # split id in half
        # check if halfs are equal
        # if so then add the id to total
        s = str(num)
        mid = len(s) // 2
        left = s[:mid]
        right = s[mid:]
        if left == right:
            cur_total += num
    return cur_total
    
def process_range_part2(start, end):
    cur_total = 0

    for num in range(start, end + 1):
        s = str (num)
        length = len(s)
        found = False

        for chunk in range(1, length//2 + 1):
            pattern = f"^(.{{{chunk}}})\\1+$"
            if (re.fullmatch(pattern, s)):
                found = True
                break

        if (found):
            cur_total += num
    
    return cur_total


for id in ids:
    # process each range
    cur_range = id.split('-')
    start = int(cur_range[0])
    end = int(cur_range[1])
    total1 += process_range_part1(start, end)
    total2 += process_range_part2(start, end)


print (total1)
print (total2)

