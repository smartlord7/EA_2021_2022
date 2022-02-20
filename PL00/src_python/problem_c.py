import input as inp


def main():
    array = list()

    while True:
        line = inp.readln()

        if not line:
            break

        value = int(line)

        array.append(value)

    array.sort()

    for value in array:
        print(value)


if __name__ == '__main__':
    main()
