$(document).ready(function() {
	var sayHello = new LiveValidation( "j_password", { validMessage: "", wait: 500 } );
	sayHello.add( Validate.Presence, {failureMessage : "Password is a mandatory !" } );
	sayHello.add( Validate.Length, { tooShortMessage:'Password must have at least  {minimum} characters !' , minimum: 4  } );
});