from sha256_new1 import sha_256
from hashlib import sha256
from hmac import hmac



def generate_aes_key(key):
    h = sha256(key)
    res = h.digest()[:16]
    return res


if __name__ == "__main__":
    h1 = sha_256('algebra')
    print(h1)
    print('--------------------------')


    h = hmac(b"love", b"We are running out of time", sha256)
    print(h.hexx())
    print('--------------------------')

    aes_gen = generate_aes_key(b"cryptography")
    print(8 * len(aes_gen), aes_gen)



