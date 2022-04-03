package com.project.constant;

import org.springframework.stereotype.Service;

@Service
public class OrderConstants {

   public static final String RESOURCE_WITH_ID_NOT_FOUND = "No order with the given id was found!";
   public static final String RESOURCE_WITH_INVALID_ID = "The passed id is invalid!";
   public static final String NO_RESOURCE_FOUND = "No order was found!";
   public static final String INVALID_RESOURCE_FORMAT = "The order that you are trying to save is invalid!";
}
