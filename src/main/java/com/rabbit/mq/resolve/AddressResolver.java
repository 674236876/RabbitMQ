package com.rabbit.mq.resolve;

import com.rabbitmq.client.Address;

import java.io.IOException;
import java.util.List;

public interface AddressResolver {

  List<Address> getAddresses() throws IOException;

}
