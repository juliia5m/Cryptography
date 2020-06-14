
from random import randint

def miller_rabin_test(n, k = 300):


    if n == 0 or n == 1 or n == 4 : # although we don`t give the result for one, but fix as composite here
        return False
    elif n == 2 or n == 3 or n == 5:
        return True
    elif n & 1 == 0:
        return False
    s = 0
    d = n - 1
    while d % 2 == 0:
        s = s + 1
        d = d >> 1   # returns d with the bits shifted to the right by 1 place.
    for i in range(k):
        a = randint(2, n-2)
        b = pow(a, d, n)
        if b == 1 or b == n-1:
            continue
        for i in range(s-1):
            b = pow(b, 2, n)
            if b == 1:
                return False
            elif b == n - 1:
                a = 0
                break
        if a:
            return False
    return True



if __name__ == "__main__":
    print(miller_rabin_test(57))