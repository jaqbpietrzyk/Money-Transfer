## How to run

Just type 

```
./gradlew run
```

By default application will listen on 4567 http port.

## How to use

Money Transfer app is as simple as possible. It has only one HTTP Post endpoint '/transfer'. By default it binds at <http://localhost:4567/transfer>

In HTTP Post body please provide following json structure:

```json
{
	"from": ?,
	"to" : ?,
	"currency" : ?,
	"amount" : ?
}
```

Where:
* __from__ has to be a valid IBAN number. Moreover, there must be sufficient funds to make a transaction. Please refer to: [IBAN Wiki](https://en.wikipedia.org/wiki/International_Bank_Account_Number)
* __to__ has to be a valid IBAN number. Please refer to: [IBAN Wiki](https://en.wikipedia.org/wiki/International_Bank_Account_Number)
* __currency__ for simplification there are only 3 valid currencies: USD, EUR, PLN. It's not possible make transfer money between accounts in different currency.
* __amount__ non-negative, at most two digits after decimal point number 

## Quick Start

After app initialization, there will be 4 available test accounts:


* IBAN: AL35202111090000000001234567 AMOUNT: 143.40 CURRENCY: EUR
* IBAN: AD1400080001001234567890 AMOUNT: 1322.43 CURRENCY: EUR
* IBAN: AT483200000012345864 AMOUNT: 12321.32 CURRENCY: USD
* IBAN: PL10105000997603123456789123 AMOUNT: 100.00 CURRENCY: PLN

To play with API, just use some simple HTTP client like Postman or Curl. Example payload:



```json
{
	"from": "FR7630006000011234567890189",
	"to" : "PL61109010140000071219812874",
	"currency" : "USD",
	"amount" : "10.10"
}
```

```
curl --header "Content-Type: application/json" \
 --request POST \
 --data '{"from" : "AL35202111090000000001234567","to" : "AD1400080001001234567890","amount" : "10.50","currency" : "EUR"}' \
http://localhost:4567/transfer
```