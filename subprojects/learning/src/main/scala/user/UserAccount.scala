package learning.domain.user

import com.github.nscala_time.time.Imports.DateTime

case class UserAccount (
   username: String,
   salt: String,
   hashedPassword: String,
   emailConfirmed: Boolean,
   registeredDateTime: DateTime
)
