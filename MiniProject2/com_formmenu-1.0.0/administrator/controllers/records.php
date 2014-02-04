<?php
/**
 * @version     1.0.0
 * @package     com_formmenu
 * @copyright   Copyright (C) 2014. All rights reserved.
 * @license     GNU General Public License version 2 or later; see LICENSE.txt
 * @author      Richard <ThreadOfEthAriadne@gmail.com> - 
 */

// No direct access
defined('_JEXEC') or die;

jimport('joomla.application.component.controllerform');

/**
 * Records controller class.
 */
class FormmenuControllerRecords extends JControllerForm
{

    function __construct() {
        $this->view_list = 'recordss';
        parent::__construct();
    }

}