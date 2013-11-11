"use strict";

angular.module("lotocado.translate", ["pascalprecht.translate"]).
	config(["$translateProvider", function ($translateProvider) {
		$translateProvider.translations("fr", {
			APPNAME: "Lotocado",
			HOME: "Accueil",
			ABOUT: "À propos",
			HOME_CREATE_EVENT: "Créer un évènement",
			HOME_WHAT_IS_FOR : "À quoi sert",
			HOME_WHAT_IS_FOR_1 : "est une application gratuite qui permet d'organiser le tirage au sort des cadeaux de Noël. Chaque personne reçoit un cadeau unique et personnalisé d'une personne qui sera choisie au hasard.",
			HOME_WHAT_IS_FOR_2 : "Un organisateur est responsable de créer l'évènement et de renseigner l'ensemble des participants. Chaque participant reçoit un email contenant le lien vers le résultat de son tirage. Il découvre ainsi qui aura la chance de recevoir un cadeau de sa part.",
			HOME_WHAT_IS_FOR_3 : "Le tirage étant secret, même l'organisateur ne connaît pas le résultat. Il faudra donc patienter jusqu'à Noël pour savoir qui vous offrira votre cadeau !",
			HOME_HOW_DOES_IT_WORK : "Comment ça marche",
			HOME_HOW_DOES_IT_WORK_1 : "Un organisateur est chargé de créer un évènement en renseignant l'ensemble des participants",
			HOME_HOW_DOES_IT_WORK_2 : "Il est possible de décider si certaines personnes ne peuvent pas recevoir de cadeau de la part d'autres personnes (exclusions)",
			HOME_HOW_DOES_IT_WORK_3 : "Une fois les informations saisies et vérifiées,",
			HOME_HOW_DOES_IT_WORK_4 : "réalise le tirage au sort et envoie un email à chaque participant",
			BREADCRUMB_CREATION : "Création",
			BREADCRUMB_PARTICIPANTS : "Participants",
			BREADCRUMB_EXCLUSIONS : "Exclusions",
			BREADCRUMB_VERIFICATION : "Vérification",
			PREVIOUS : "Retour",
			NEXT : "Étape suivante",
			CREATION_EVENT : "Création de l'évènement",
			CREATION_EVENT_NAME : "Nom de l'évènement",
			CREATION_EVENT_NAME_HELP : "exemple : Noël 2013",
			CREATION_ORGANIZER_NAME : "Votre prénom",
			CREATION_ORGANIZER_MAIL : "Votre email",
			CREATION_DATE : "Date de l'évènement",
			PARTICIPANTS_ADD_PARTICIPANTS : "Participants de l'évènement",
			PARTICIPANTS_ADD_PARTICIPANTS_INFO : "Un minimum de 3 participants est requis",
			PARTICIPANTS_NAME : "Prénom",
			PARTICIPANTS_EMAIL : "Email",
			PARTICIPANTS_ADD_PARTICIPANT : "Ajouter un participant",
			EXCLUSIONS_EDIT : "Exclusions de l'évènement",
			EXCLUSIONS_INFO : "Une personne ne pourra pas tirer au sort une personne qui a été exclue (ou plusieurs personnes) ",
			EXCLUSIONS_FOR : "Exclusions pour",
			VERIFICATION : "Vérification des informations de l'évènement",
			VERIFICATION_INFO : "Aucune des informations saisies ne pourra être modifiée après cette étape",
			VERIFICATION_PARTICIPANTS : "Liste des participants",
			VERIFICATION_SAVE : "Enregister l'évènement",
			VERIFICATION_NOT_PERSISTED_ERROR_CODE : "Une erreur s'est produite",
			VERIFICATION_NO_RESULT_ERROR_CODE : "Une erreur s'est produite durant le tirage au sort, aucun résultat n'est possible. Veuillez modifier les règles d'exclusion",
			VERIFICATION_SEND_MAIL_TO_ORGANIZER_ERROR_CODE : "Une erreur s'est produite, l'envoie du mail à l'organisateur a échoué",
			VERIFICATION_SEND_MAIL_TO_PARTICIPANT_ERROR_CODE : "Une erreur s'est produite, l'envoie des mails aux participants a échoué",
			VERIFICATION_DATA_ERROR_CODE : "Une erreur s'est produite, veuillez vérifier les données de l'évènement",
			PARTICIPANT_RESULT_1 : "Découvrez le résultat",
			PARTICIPANT_RESULT_2 : "du tirage au sort",
			PARTICIPANT_RESULT_3 : "pour l'évènement",
			PARTICIPANT_RESULT_4 : "tu auras la chance d'offrir un cadeau à",
			PARTICIPANT_ERROR : "Une erreur s'est produite. Le résultat du tirage au sort n'a pas pu être trouvé",
			SUCCESS_EVENT : "L'évènement",
			SUCCESS_CREATED : "a bien été créé",
			SUCCESS_PARTICIPANTS : "Chaque participant a reçu une invation par mail pour consulter le résultat du tirage au sort.",
			SUCCESS_ADMIN : "Un email vous a également été envoyé afin de suivre l'évènement. Il est ainsi possible de savoir si les participants ont consulté le résultat du tirage. Si vous n'avez pas reçu d 'email, conserver le lien ci-dessous vers la page d'administration :",
			SUCCESS_THANK_YOU : "Merci d'avoir utilisé notre service et bonnes fêtes !",
			ABOUT_TITLE : "À propos de",
			ABOUT_LOTOCADO : "est une application gratuite.",
			ABOUT_CONTACT : "Si vous souhaitez nous faire des remarques, signalez un problème ou tout simplement nous donner vos impressions, vous pouvez nous conctacter à l'adresse suivante :",
			ABOUT_DONATION : "Si cette application vous a plu, vous pouvez faire un don et ainsi contribuer à l'amélioration du service.",
			EVENT : "Évènement",
			EVENT_ERROR : "Une erreur s'est produite. L'évènement n'a pas pu être chargé",
			EVENT_PARTICIPANTS : "Liste des participants",
			EVENT_RESULT_CONSULTED : "Résultat consulté",
			EVENT_RESULT_NOT_CONSULTED : "Résultat non consulté"
		});
		$translateProvider.preferredLanguage("fr");
	}]);