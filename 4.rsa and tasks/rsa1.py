import func
from random import randint

#-------------------------------------------------------------------------

def encrypt(e: int, n: int, message: str):
    text = []
    for i in range(len(message)):
        m = ord(message[i])
        text.append(m)

    ctext = [pow(i, e, n) for i in text]

    return ctext

def decrypt(d, n, ctext):
    text = []
    for i in ctext:
        m = pow(i, d, n)
        m1 = chr(m)
        text.append(m1)
    return  "".join(text)


def key_generation(bits):
    if not bits % 4 == 0:
        raise AssertionError

    p, q = func.get_p_q(bits // 2)
    phi = func.phi(p, q)

    e = 0

    while e == 0 or e > phi or func.gcd(e, phi) != 1:
        e = randint(16, phi)

    d = func.modInverse(e, phi)
    if not (e * d) % phi == int(1):
        raise AssertionError ('Got an error here')

    return p*q, e, d






