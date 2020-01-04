package com.n.crypt.service

class PasswordGenerator {

    private val lowerCaseLetters = "qwertyuiopasdfghjklzxcvbnm"
    private val upperCaseLetters = lowerCaseLetters.toUpperCase()
    private val numbers = "1234567890"
    private val simpleChars = ".,;:!@#\$%^&*?|-_+="
    private val complexChars = "()[]{}<>/\\'"

    fun generatePassword(
        useLowerCaseLetters: Boolean,
        useUpperCaseLetters: Boolean,
        useNumbers: Boolean,
        useSimpleChars: Boolean,
        useComplexChars: Boolean
    ): String {

        var totalCharactersToUse = ""
        var generatedPassword = ""

        if (useLowerCaseLetters) totalCharactersToUse = totalCharactersToUse.plus(lowerCaseLetters)
        if (useUpperCaseLetters) totalCharactersToUse = totalCharactersToUse.plus(upperCaseLetters)
        if (useNumbers) totalCharactersToUse = totalCharactersToUse.plus(numbers)
        if (useSimpleChars) totalCharactersToUse = totalCharactersToUse.plus(simpleChars)
        if (useComplexChars) totalCharactersToUse = totalCharactersToUse.plus(complexChars)

        for (i in 0..10) {
            generatedPassword = generatedPassword.plus(totalCharactersToUse[(totalCharactersToUse.indices).shuffled().first()].toString())
        }

        return generatedPassword
    }
}