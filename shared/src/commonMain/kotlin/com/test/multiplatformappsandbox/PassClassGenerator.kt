package com.test.multiplatformappsandbox


class PassClassGenerator {
    fun createUnSignedJwt(time: Long, passId: String): String {
        val issuerEmail = "gilpass91@gmail.com"
        return """
        {
          "iss": $issuerEmail,
          "aud": "google",
          "typ": "savetowallet",
          "iat": $time,
          "origins": [],
          "payload": {
              "genericObjects": [${generatePassObject(passId)}]
          }
        }
    """.trimIndent()
    }

    fun generatePassObject(passId: String): String {
        val classId = "3388000000022263096.test_id"
        val issuerId = "3388000000022263096"
        return """
        {
          "id": "$issuerId.$passId",
          "classId": $classId,
          "genericType": "GENERIC_TYPE_UNSPECIFIED",
          "cardTitle": {
            "defaultValue": {
              "language": "en",
              "value": "Test Generic Pass"
            }
          },
          "subheader": {
            "defaultValue": {
              "language": "en",
              "value": "Attendee"
            }
          },
          "header": {
            "defaultValue": {
              "language": "en",
              "value": "Gil Akrish"
            }
          },
          "logo": {
            "sourceUri": {
              "uri": "https://storage.googleapis.com/wallet-lab-tools-codelab-artifacts-public/pass_google_logo.jpg"
            }
          }
        }
    """.trimIndent()
    }
}