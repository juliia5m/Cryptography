import random
import miller_rabbin

def phi(p,q):
    return (p-1)*(q-1)

def gcd(a, b):
    while b != 0:
        a, b = b, a % b
    return a


def primes(bits):
    while True:
            p = random.getrandbits(bits)
            p |= 2**bits | 1
            if miller_rabbin.miller_rabin_test(p):
                return p

def rrange(point1, point2):
    while True:
        p = random.randrange(point1, point2-1)
        p |= 1
        if miller_rabbin.miller_rabin_test(p):
                return p



def egcd(a, b):
    u = 1
    u1 = 0
    v = 0
    v1 = 1
    while b:
        q = a // b
        u, u1 = u1, u - q * u1

        v, v1 = v1, v - q * v1
        a, b = b, a - q * b
    return u, v, a

def modInverse(e,n):
    p = egcd(e,n)[0]
    inv = p % n
    return inv


def get_p_q(bits=1024):
    if not bits % 4 == 0:
        raise AssertionError

    p = primes(bits)
    q = rrange(p + 1, 2 * p)

    return p, q