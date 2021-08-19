package hw02

object task_caesar {

    /**
     * В данном задании Вам предлагается реализовать функции,
     * реализующие кодирование/декодирование строки шифром Цезаря.
     * https://ru.wikipedia.org/wiki/Шифр_Цезаря
     * Алфавит - прописные латинские буквы от A до Z.
     * Сдвиг   - неотрицательное целое число.
     * Пример: при сдвиге 2 слово "SCALA" шифруется как "UECNC".
     */

    /**
     * @param word   входное слово, которое необходимо зашифровать
     * @param offset сдвиг вперёд по алфавиту
     * @return зашифрованное слово
     */
    def encrypt(word: String, offset: Int): String = word
      .map(c => (c + (offset % 26) - (if ((c + offset % 26) > 'Z') 1 else  0) * 26).toChar)

    // первая версия
    def encrypt2(word: String, offset: Int): String = word
      .map(c => if ((c + offset % 26) <= 'Z') (c + (offset % 26)).toChar else (c + (offset % 26) - 26).toChar)

    /**
     * @param cipher шифр, который необходимо расшифровать
     * @param offset сдвиг вперёд по алфавиту
     * @return расшифрованное слово
     */
    def decrypt(cipher: String, offset: Int): String = cipher
      .map(c => (c + (if ((offset % 26) > (c - 'A')) 1 else 0) * 26 - offset % 26).toChar)

    // первая версия
    def decrypt2(cipher: String, offset: Int): String = cipher
      .map(c => if (c - 'A' < offset % 26) (c + 26 - offset % 26).toChar else (c - offset % 26).toChar)

    def main(args: Array[String]): Unit = {
        println(encrypt("ABCD", 2))
        println(decrypt(encrypt("ABCD", 2), 2))
        println(decrypt2(encrypt("ABCD", 2), 2))
    }
}
