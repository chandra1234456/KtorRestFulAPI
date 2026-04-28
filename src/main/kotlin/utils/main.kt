package utils

import utils.Constants.SECRET_KEY

fun main(){

    val requestJson = """{"email":"test@test.com"}"""
    val encryptedResponse = AESUtil.encrypt(requestJson, SECRET_KEY)

    println("Encrypt ed: $encryptedResponse")

    val decrypted = AESUtil.decrypt(encryptedResponse, SECRET_KEY)

    println("Decrypted: $decrypted")


}