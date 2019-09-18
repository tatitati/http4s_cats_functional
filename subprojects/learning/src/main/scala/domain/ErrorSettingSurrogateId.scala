package learning.domain.user

sealed trait ErrorSettingSurrogateId
case object SurrogateIdAlreadySet extends ErrorSettingSurrogateId
case object SurrogateIdCannotSetToNone extends ErrorSettingSurrogateId