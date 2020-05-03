Feature: rental

  Scenario: create film rental
    Given available film
      | filmId | name  | type | stock |
      | f1     | RAMBO | OLD  | 1     |
    Given customer with data
      | customerID | subscription | bonus |
      | 1          | PREMIUM      | 0     |
    When create a rental with input
      | customerID | filmId | subscription | type | numberOfDays | startDate  |
      | 1          | f1     | PREMIUM      | OLD  | 10           | 2021-05-05 |
      Then create same rental fail as stock in 0
      | customerID | filmId | subscription | type | numberOfDays | startDate  |
      | 1          | f1     | PREMIUM      | OLD  | 10           | 2021-05-05 |
    Then available film view is eventually updated
      | filmId | name  | type | stock |
      | f1     | RAMBO | OLD  | 0     |
    Then customer view is eventually updated
      | filmId | customerID | price | numberOfDays | startDate  | bonus |
      | f1     | 1          | 6     | 10           | 2021-05-05 | 1     |

