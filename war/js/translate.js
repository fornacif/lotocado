"use strict";

angular.module("lotocado.translate", ["pascalprecht.translate"]).
	config(["$translateProvider", function ($translateProvider) {
		$translateProvider.translations("fr", {
			LOTOCADO: "Lotocado",
			HOME: "Accueil",
			ABOUT: "A propos",
			HOME_CREATE_EVENT: "Créer un évènement",
			HOME_WHAT_IS_FOR : "A quoi sert",
			HOME_WHAT_IS_FOR_1 : "est une application gratuite qui permet d'organiser le tirage au sort des cadeaux de Noël. Chaque personne reçoit un cadeau unique et personnalisé d'une personne qui sera choisie au hasard.",
			HOME_WHAT_IS_FOR_2 : "Un organisateur est responsable de créer l'évènement et de renseigner l'ensemble des participants. Chaque participant reçoit un email contenant le lien vers le résultat de son tirage. Il découvre ainsi qui aura la chance de recevoir un cadeau de sa part.",
			HOME_WHAT_IS_FOR_3 : "Le tirage étant secret, même l'organisateur ne connaît pas le résultat. Il faudra donc patienter jusqu'à Noël pour savoir qui vous offrira votre cadeau !",
			HOME_HOW_DOES_IT_WORK : "Comment ça marche",
			HOME_HOW_DOES_IT_WORK_1 : "Je, c'est à dire l'organisateur, crée un évènement avec tous les participants",
			HOME_HOW_DOES_IT_WORK_2 : "Je décide si certaines personnes ne peuvent pas recevoir de cadeau de la part d'autres personnes (exclusions)",
			HOME_HOW_DOES_IT_WORK_3 : "Et finalement je confirme les informations saisies. Ensuite,",
			HOME_HOW_DOES_IT_WORK_4 : "réalise le tirage au sort et envoie un email à chaque participant",
			BREADCRUMB_CREATION : "Création",
			BREADCRUMB_PARTICIPANTS : "Participants",
			BREADCRUMB_EXCLUSIONS : "Exclusions",
			BREADCRUMB_CONFIRMATION : "Confirmation",
			NEXT_STEP : "Etape suivante",
			CREATION_EVENT : "Création d'un évènement",
			CREATION_ENTER_EVENT_NAME : "Saisir le nom de l'évènement",
			CREATION_EVENT_NAME_HELP : "exemple : Noël 2013",
			CREATION_ENTER_ORGANIZER_NAME : "Saisir votre prénom",
			CREATION_ENTER_ORGANIZER_MAIL : "Saisir votre email",
			CREATION_ENTER_DATE : "Saisir la date de l'évènement"
		});
		$translateProvider.preferredLanguage("fr");
	}]);