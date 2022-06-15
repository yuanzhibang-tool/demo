<?php

class DHOpenAuthErrorException extends Exception
{

    public $extra;

    function __construct($message, $code, $extra = null)
    {
        parent::__construct($message, $code);
        $this->extra = $extra;
    }
}
