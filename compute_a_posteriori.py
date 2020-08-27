import sys

def start_display(obs):
    file = open('result.txt', 'a+')
    file.write(f'Observation sequence Q : {obs}\n')
    file.write(f'Length of Q : {len(obs)}\n')
    file.write('\n')
    file.close()

# cal = [i, obs list, P(h1|Q), P(h2|Q), P(h3|Q), P(h4|Q), P(h5|Q),P(nextCandyC), P(nextCandyL)]
def F_result(cal):
    file = open('result.txt', 'a+')
    file.write(f'After Observation {cal[0]} = {cal[1]}\n')
    file.write('\n')
    file.write('P(h1 | Q) = {:.5}\n'.format(cal[2]))
    file.write('P(h2 | Q) = {:.5}\n'.format(cal[3]))
    file.write('P(h3 | Q) = {:.5}\n'.format(cal[4]))
    file.write('P(h4 | Q) = {:.5}\n'.format(cal[5]))
    file.write('P(h5 | Q) = {:.5}\n'.format(cal[6]))
    if (cal[1]==''):
        file.write('Probability that the next candy will be candy C, given no observation: {:.5}\n'.format(cal[7]))
        file.write('Probability that the next candy will be candy L, given no observation: {:.5}\n'.format(cal[8]))
    else:
        file.write('Probability that the next candy will be C, given Q: {:.5}\n'.format(cal[7]))
        file.write('Probability that the next candy will be L, given Q: {:.5}\n'.format(cal[8]))
        file.write('\n')
    file.close()


def compute(obs):
    prior = [0.1, 0.2, 0.4, 0.2, 0.1, 0.5, 0.5]
    # prior[5] and prior[6] are for cherry probability and line probability respectively.
    cherry = [1.0, 0.75, 0.50, 0.25, 0.0]
    lime = [0.0, 0.25, 0.50, 0.75, 1.0]

    obsList = list(obs)

    if len(obsList) == 0:
        F_result([1, '', prior[0], prior[1], prior[2], prior[3], prior[4], prior[5], prior[6]])

    for x in range(len(obsList)):
        h = []
        startC = 0.0
        startL = 0.0
        nextC = 0.0
        nextL = 0.0
        for i in range(5):
            startC += prior[i] * cherry[i]
            startL += prior[i] * lime[i]
        if obsList[x] == 'C':
            for j in range(5):
                temp = (prior[j] * cherry[j]) / startC
                prior[j] = temp
                h.append(temp)
        elif obsList[x] == 'L':
            for j in range(5):
                temp = (prior[j] * lime[j]) / startL
                prior[j] = temp
                h.append(temp)
        for i in range(5):
            nextC += prior[i] * cherry[i]
            nextL += prior[i] * lime[i]
        F_result([x+1, obsList[x], h[0], h[1], h[2], h[3], h[4], nextC, nextL])


def main():
    if len(sys.argv) == 1:
        obs = ''
    else:
        obs = sys.argv[1]
    file = open('result.txt', 'w+')
    file.write(f'Observation sequence Q : {obs}\n')
    file.write(f'Length of Q : {len(obs)}\n')
    file.write('\n')
    compute(obs)


if __name__ == '__main__':
    main()