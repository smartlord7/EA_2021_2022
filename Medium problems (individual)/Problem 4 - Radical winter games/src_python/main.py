def solve(triangle):
    memo = dict()

    return solve_(triangle, memo, 0, 0)


def solve_(triangle, memo, x, y):
    l = len(triangle)

    if x == l - 1:
        return triangle[x][y]

    key = (x, y)

    if key in memo:
        return memo[key]

    sol = max(triangle[x][y] + solve_(triangle, memo, x + 1, y), triangle[x][y] + solve_(triangle, memo, x + 1, y + 1))

    memo[key] = sol

    return sol


def main():
    n_cases = int(input())

    for i in range(n_cases):
        triangle = list()
        n_rows = int(input())

        for j in range(n_rows):
            line = input().split()
            row = list()
            for k in range(len(line)):
                row.append(int(line[k]))

            triangle.append(row)

        print(solve(triangle))


if __name__ == '__main__':
    main()
