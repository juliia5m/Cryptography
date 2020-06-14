import func
import math
import hashlib
import os
import typing
import random


our_key = typing.Tuple[int, int]  # borrowed idea of  key representation, created better in rsa :(



def sha256(message) :

    hash = hashlib.sha256()
    hash.update(message)
    needed = hash.digest()
    return needed

# did not write os2ip, i2osp and key length from standard as functions here, just write them as a part of other func

def mask_generation_function_1(s, lenn) :    #is a cryptographic primitive similar to a
    temp = b''                               #cryptographic hash function except that while a
    length = 128
    get_ceil = math.ceil(lenn / length)      #hash function's output is a fixed size, a MGF supports output of a variable length
    for c in range(0, get_ceil):
        new_c = c.to_bytes(4, byteorder='big')
        temp += sha256(s + new_c)
    res = temp[:lenn]
    return res

def XOR(our_d, xor_to_do) :
    xored = b''
    our_d_len , xor_to_do_len = len(our_d), len(xor_to_do)

    maximum = max(our_d_len, xor_to_do_len)
    for i in range(maximum):
        if i < our_d_len and i < xor_to_do_len:
            x_or = our_d[i] ^ xor_to_do[i]
            a = x_or.to_bytes(1, byteorder='big')
            xored += a
        elif i < our_d_len:
            b = our_d[i].to_bytes(1, byteorder='big')
            xored += b
        else:
            break
    return xored

def key_generation(bits, n=None):   # from rsa, but a little bit modified (return type changed)
    if not bits % 4 == 0:
        raise AssertionError

    p, q = func.get_p_q(bits // 2)
    phi = func.phi(p, q)

    e = 0

    while e == 0 or e > phi or func.gcd(e, phi) != 1:
        e = random.randint(16, phi)

    d = func.modInverse(e, phi)
    if not (e * d) % phi == int(1):
        raise AssertionError ('Got an error here')

    return ((e, n), (d, n))





def encription_oaep(M, publicKey: our_key) :
    varr = 16
    j, n = publicKey
    k = n.bit_length() // 8
    lenn = len(M)
    needed = k - varr - 2
    if not lenn <= needed:
        raise AssertionError

    get_hash = sha256(b'')
    hash_len = len(get_hash)
    for_padding = k - lenn - 2 * hash_len - 2
    bb = b'\x00'
    b1 = b'\x01'

    ps = bb * for_padding

    got_d = get_hash + ps + b1 + M


    random_b = os.urandom(hash_len)  # to print the random bytes string

    var = k - hash_len- 1

    operation_1 = mask_generation_function_1(random_b, var)
    operation_2 = XOR(got_d, operation_1)
    operation_3 = mask_generation_function_1(operation_2, hash_len)
    get_xor = XOR(random_b, operation_3)

    EM = bb + get_xor + operation_2
    m = int.from_bytes(EM, byteorder='big')
    c = main(publicKey, m)
    got_crypto = c.to_bytes(k, byteorder='big')
    return got_crypto

def decription_oaep(privateKey: our_key, our_crypto ):

    get_hash = sha256(b'')
    lengthh = 16
    j, n = privateKey
    k = n.bit_length() // 8

    if not len(our_crypto) == k:
        raise AssertionError

    c = int.from_bytes(our_crypto, byteorder='big')
    d, n = privateKey
    m = pow(c, d, n)

    E_M = m.to_bytes(k, byteorder='big')

    var1 = E_M[:1]
    var2 = E_M[1:1 + lengthh]
    var3 = E_M[1 + lengthh:]

    mgf = mask_generation_function_1(var3, lengthh)
    x_or = XOR(var2, mgf)
    mgf_d = mask_generation_function_1(x_or, k - lengthh - 1)
    D_B = XOR(var3, mgf_d)

    hash_modif = D_B[:lengthh]

    if not get_hash == hash_modif:
        raise AssertionError

    l = lengthh
    while l < len(D_B):
        if D_B[l] == 0:
            l += 1
            continue
        elif D_B[l] == 1:
            l += 1
            break
        else:
            raise Exception()
    message = D_B[l:]
    return message



def main(publicKey: our_key, message ) :
    e, n = publicKey
    c = pow(message, e, n)
    return c

