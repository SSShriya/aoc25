# Initialise variables: 
# password1 is the answer to part 1, 
# password2 is the answer to part 2,
# cur_position is the position after a certain dial turn: starts at 50
password1 = 0
password2 = 0
cur_position = 50

with open ('day1data.txt', 'r') as file:
    for line in file:
        # get full rotation string
        action = line.strip()
        # separate the direction and rotation
        direction = action[0]
        rotation = int(action[1:])

        # if the rotation is greater than a full dial turn, 
        # find how many times it crosses '0' by dividing the rotation by 100
        # set the new rotation to be the remainder 
        if (rotation > 100):
            password2 += rotation // 100
            rotation = rotation % 100
        
        # if the direction is L
        if (direction == 'L'):
            # for part 2
            # if subtracting the remainder of the rotation is negative, add
            # 1 to password as long as the cur_position is not already 0
            if (cur_position - rotation <= 0 and cur_position != 0):
                password2 += 1 
            
            # if the direction is left, we need to subtract 'rotation'
            rotation = -rotation
           
        elif (direction == 'R'):
            # for part 2
            # if adding the remainder of the rotation is positive, add
            # 1 to password 
            if (cur_position + rotation >= 100):
                password2 += 1

        # find the new position by adding the rotation value
        # and ensuring it is in bounds
        cur_position = cur_position + rotation
        if (cur_position < 0):
            cur_position += 100
        elif (cur_position >= 100):
            cur_position -= 100

        # for part 1: increment password if the dial ends on 0
        if (cur_position == 0):
            password1 += 1

print (password1)
print (password2)