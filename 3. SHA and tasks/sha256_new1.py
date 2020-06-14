from collections import deque

K = [0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
         0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
         0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
         0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
         0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
         0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
         0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
         0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
         0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
         0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
         0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
         0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
         0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
         0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
         0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
         0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2]

h0 = [0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
      0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19]



def maj(x,y,z):
    a = (x & y)
    b = (x & z)
    c = (y & z)
    res = a ^ b ^ c
    return res


def ch(x,y,z):
    a = x & y
    b = (~ x & z)
    return a ^ b


def add_one(string):
    return string + "1"

def sum0(bits):
    seq = deque(bits)
    a = int(rotate(seq.copy(), 2), 2)
    b = int(rotate(seq.copy(), 13), 2)
    c = int(rotate(seq.copy(), 22), 2)
    x = a ^ b ^ c
    return x


def sum1(bits):
    seq = deque(bits)
    a = int(rotate(seq.copy(), 6), 2)
    b = int(rotate(seq.copy(), 11), 2)
    c = int(rotate(seq.copy(), 25), 2)
    x = a ^ b ^ c
    return x




def rotate(sequence, n):
    sequence.rotate(n)   # used deque method
    return ''.join(sequence)


def padding(string):
    n = 512
    b = '032b'
    needed_str = add_one(string) + "0" * (n - len(add_one(string)) - len(format(len(string), b))) + ''.join(format(len(string), b))
    return needed_str


def sigma0(bits):
    seq = deque(bits)
    a = int(rotate(seq.copy(), 7), 2)
    b = int(rotate(seq.copy(), 18), 2)
    c = int(bits, 2) >> 3

    return a ^ b ^ c


def sigma1(bits):
    seq = deque(bits)
    a = int(rotate(seq.copy(), 17), 2)
    b = int(rotate(seq.copy(), 19), 2)
    c = int(bits, 2) >> 10
    return a ^ b ^ c


def sha_256(message):
    '''binSt = ""
    for char in string:
        binSt = add_padding(''.join(format(ord(char), '08b')))'''
    b = '08b'
    bin_and_pad = padding(''.join(format(ord(char), b) for char in message))
    splitt = []
    for i in range(32, 513, 32):
        splitt.append(bin_and_pad[i - 32:i])

    a = h0[0]
    b = h0[1]
    c = h0[2]
    d = h0[3]
    e = h0[4]
    f = h0[5]
    g = h0[6]
    h = h0[7]


    n = 64
    m = 15
    b1 = '032b'
    for i in range(n):
        if i > m:
            splitt.append(format((sigma1(splitt[i - 2]) + sigma0(splitt[i - 15]) + int(splitt[i - 7],2) + int(splitt[i - 16], 2)) % 2 ** 32, b1))
        _t = (h + sum1(format(e, b1)) + ch(e, f, g) + K[i] + int(splitt[i], 2)) % 2 ** 32
        _t2 = (sum0(format(a, b1)) + maj(a, b, c)) % 2 ** 32

        h, g, f, e, d, c, b, a = g, f, e, (d + _t) %2 ** 32, c, b, a, (_t + _t2) %2 ** 32
    h0[0] = (a + h0[0]) % 2 ** 32
    h0[1] = (b + h0[1]) % 2 ** 32
    h0[2] = (c + h0[2]) % 2 ** 32
    h0[3] = (d + h0[3]) % 2 ** 32
    h0[4] = (e + h0[4]) % 2 ** 32
    h0[5] = (f + h0[5]) % 2 ** 32
    h0[6] = (g + h0[6]) % 2 ** 32
    h0[7] = (h + h0[7]) % 2 ** 32

    got = '0x'
    zero = got + hex(h0[0])[2:]
    one = got + hex(h0[1])[2:]
    two = got + hex(h0[2])[2:]
    three = got + hex(h0[3])[2:]
    four = got + hex(h0[4])[2:]
    five = got + hex(h0[5])[2:]
    six = got + hex(h0[6])[2:]
    seven = got + hex(h0[7])[2:]

    '''got = '0x'

    for i in range(0, 8):
        got += hex(h0[i])[2:]
    return got'''

    return (zero, one, two, three, four, five, six, seven)

#print(sha_256('algebra'))