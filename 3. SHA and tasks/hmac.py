
from zlib import crc32, adler32
from hashlib import sha256
#from sha256_new1 import sha_256



class hmac:

    def __init__(self, key, message, hash=sha256):

        self.key = key
        self.message = message
        self.size = 64
        self.hash = hash
        self.in_pading = bytearray()
        self.out_pading = bytearray()
        self.f = False

    def pad(self):

        for i in range(self.size):
            self.in_pading.append(0x36 ^ self.key[i])
            self.out_pading.append(0x5c ^ self.key[i])

    def i_key(self):

        b = b"\x00"
        s = self.size

        l = len(self.key)
        if l > s:
            self.key = bytearray(sha256(self.key).digest())
        elif l < s:
            i = l
            while i < s:
                self.key += b
                i += 1

    def digest(self):

        h1 = adler32
        h2 = crc32
        if self.hash == h1 or self.hash == h2:
            res = self.hash(bytes(self.out_pading) + str(self.hash(bytes(self.in_pading) + self.message)).encode())
            return res

        if self.f == False:
            self.i_key()
            self.pad()

            self.f = True
        res1 = self.hash(bytes(self.out_pading) + self.hash(bytes(self.in_pading) + self.message).digest()).digest()

        return res1

    def hexx(self):

        if self.hash == adler32 or self.hash == crc32:
            res =  self.hash(bytes(self.out_pading) + str(self.hash(bytes(self.in_pading) + self.message)).encode())[
                   2:]
            res = hex(res)
            return res


        if self.f == False:


            self.i_key()
            self.pad()


            self.f = True
        res1 = self.hash(bytes(self.out_pading) + self.hash(bytes(self.in_pading) + self.message).digest()).hexdigest()

        return res1


if __name__ =="__main__":
    h = hmac(b"love", b"We are running out of time", sha256)
    print(h.hexx())
