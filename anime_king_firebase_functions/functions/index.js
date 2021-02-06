const functions = require('firebase-functions');

// The Firebase Admin SDK to access Cloud Firestore.
const admin = require('firebase-admin');
admin.initializeApp();

exports.createUserProfile = functions.auth.user().onCreate((user) => {
  /** This is the default user profile.*/
  var user_object = {
    user_id : user.uid,
    level : 1,
    total_exp : 0,
    req_exp : 800,
    user_name : user.displayName/*,
    user_image : "https://i.imgur.com/TlSrnCl.png",
    user_quote_en : "Must...sleep...cozy...cookies??",
    user_quote_sp : "Debe...dormir...acogedor...galletas??"*/
  };
  return admin.firestore().doc('users/'+user.uid).set(user_object);
})

/*exports.addUserAnimeBadge = functions.auth.user().onCreate((user) => {
  *//** This is the default anime badge. Users can add earn more badges in the future.*//*
  var anime_badge = {
    image : "https://i.imgur.com/TlSrnCl.png",
    quote_en : "Must...sleep...cozy...cookies??",
    quote_sp : "Debe...dormir...acogedor...galletas??"
  };
  return admin.firestore().collection('users/'+user.uid+'/anime_badges').add(anime_badge);
})*/
