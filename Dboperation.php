<?php

class DbOperation
{
    //Database connection link
    private $con;

    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';

        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();

        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }

 /*
 * The create operation
 * When this method is called a new record is created in the database
 */
 function createContact($firstName, $lastName, $Address, $Phone , $email ){
 $stmt = $this->con->prepare("INSERT INTO contactlist (firstName,lastName, Phone , Address,email) VALUES (?, ?, ?, ? ,?)");
 $stmt->bind_param("ssiss", $firstName, $lastName,  $Phone ,$Address, $email );
 if($stmt->execute())
 return true;
 return false;
 }

 /*
 * The read operation
 * When this method is called it is returning all the existing record of the database
 */
 function getContact(){
 $stmt = $this->con->prepare("SELECT id, firstName ,lastName,Address ,Phone ,email FROM contactlist");
 $stmt->execute();
 $stmt->bind_result($id, $firstName, $lastName, $Address, $Phone , $email );

 $contactlist = array();

 while($stmt->fetch()){
 $contact  = array();
 $contact['id'] = $id;
 $contact['firstName'] = $firstName;
 $contact['lastName'] = $lastName;
 $contact['Address'] = $Address;
 $contact['Phone'] = $Phone;
 $contact['email'] = $email;

 array_push($contactlist, $contact);
 }

 return $contactlist;
 }

 /*
 * The update operation
 * When this method is called the record with the given id is updated with the new given values
 */
 function updateContact( $id , $firstName, $lastName, $Address, $Phone , $email ){
 $stmt = $this->con->prepare("UPDATE contactlist SET firstName=?,lastName =?, Phone =? , Address =? ,email =?  WHERE id = ?");
 $stmt->bind_param("ssissi",$firstName, $lastName,  $Phone ,$Address, $email , $id);
 if($stmt->execute())
 return true;
 return false;
 }


 /*
 * The delete operation
 * When this method is called record is deleted for the given id
 */
 function deleteContact($id){
 $stmt = $this->con->prepare("DELETE FROM contactlist WHERE id = ? ");
 $stmt->bind_param("i", $id);
 if($stmt->execute())
 return true;

 return false;
 }
}
?>