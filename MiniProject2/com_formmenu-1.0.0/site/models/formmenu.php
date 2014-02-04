<?php
// No direct access to this file
defined('_JEXEC') or die('Restricted access');
 
// import Joomla modelitem library
jimport('joomla.application.component.modelitem');
 
/**
 * HelloWorld Model
 */
class FormMenuModelFormMenu extends JModelItem
{
        /**
         * @var string msg
         */
        protected $msg;
 
        /**
         * Get the message
         * @return string The message to be displayed to the user
         */
        public function getMsg() 
        {
                if (!isset($this->msg)) 
                {
                        $jinput = JFactory::getApplication()->input;
                        $id = $jinput->get('id', 1, 'INT');

                        switch ($id) {
                                case 2:
                                        $this->msg = 'Goodbye World!';
                                        break;
                                
                                default:
                                        $this->msg = 'Hello World!';
                                        break;
                        }

                        
                }
                return $this->msg;
        }
}