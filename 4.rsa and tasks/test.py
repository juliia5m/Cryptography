from rsa1 import key_generation, encrypt, decrypt
import rsa_oaep

if __name__ == '__main__':
    n, e, d = key_generation(1024)


    pt = 'We have almost no time left'
    print('Your message:', pt)
    print('----------------------------------------')

    ct = encrypt(e, n, pt)
    print("Your encrypted message is: ", ct)

    print('----------------------------------------')
    ptt = decrypt(d, n, ct)
    print("Your decrypted message:",ptt)
    print('----------------------------------------')
    print("test whether initial message is equal to decrypted one:", pt == ptt)

    print('----------------------------------------')
    message = pt.encode('utf-8')
    pub_keyy = key_generation(512)
    pub_keyy = n , e, d
    pub_key = e, d
    #pub_key = (55655371, 2450063722029457423241637220980347924559661426600330442718924500128814737384833494021469)
    crypto_we_got = rsa_oaep.encription_oaep(message, pub_key)

    print(crypto_we_got)

