"use strict";

angular.module("lotocado.translate", ["pascalprecht.translate"]).
	config(["$translateProvider", function ($translateProvider) {
		$translateProvider.translations("fr", {
			LOTOCADO: "Lotocado",
			HOME: "Accueil",
			ABOUT: "&Agrave; propos",
			CREATE_EVENT: "Cr&eacute;er un &eacute;v&egrave;nement",
			WHAT_IS_FOR : "&Agrave; quoi sert",
			WHAT_IS_FOR_1 : "est une application gratuite qui permet d'organiser le tirage au sort des cadeaux de No&euml;l. Chaque personne re&ccedil;oit un cadeau unique et personnalis&eacute; d'une personne qui sera choisie au hasard.",
			WHAT_IS_FOR_2 : "Un organisateur est responsable de cr&eacute;er l'&eacute;v&egrave;nement et de renseigner l'ensemble des participants. Chaque participant re&ccedil;oit un email contenant le lien vers le r&eacute;sultat de son tirage. Il d&eacute;couvre ainsi qui aura la chance de recevoir un cadeau de sa part.",
			WHAT_IS_FOR_3 : "Le tirage &eacute;tant secret, m&ecirc;me l'organisateur ne conna&icirc;t pas le r&eacute;sultat. Il faudra donc patienter jusqu'&agrave; No&euml;l pour savoir qui vous offrira votre cadeau !",
			HOW_DOES_IT_WORK : "Comment &ccedil;a marche",
			HOW_DOES_IT_WORK_1 : "Je, c'est &agrave; dire l'organisateur, cr&eacute;e un &eacute;v&egrave;nement avec tous les participants",
			HOW_DOES_IT_WORK_2 : "Je d&eacute;cide si certaines personnes ne peuvent pas recevoir de cadeau de la part d'autres personnes (exclusions)",
			HOW_DOES_IT_WORK_3 : "Et finalement je confirme les informations saisies. Ensuite,",
			HOW_DOES_IT_WORK_4 : "r&eacute;alise le tirage au sort et envoie un email &agrave; chaque participant",
			BREADCRUMB_CREATION : "Cr&eacute;ation",
			BREADCRUMB_PARTICIPANTS : "Participants",
			BREADCRUMB_EXCLUSIONS : "Exclusions",
			BREADCRUMB_CONFIRMATION : "Confirmation"
		});
		$translateProvider.preferredLanguage("fr");
	}]);