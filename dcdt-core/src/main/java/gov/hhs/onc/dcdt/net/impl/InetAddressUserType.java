package gov.hhs.onc.dcdt.net.impl;

import gov.hhs.onc.dcdt.data.types.impl.AbstractStringUserType;
import java.net.InetAddress;
import org.springframework.stereotype.Component;

@Component("inetAddrUserType")
public class InetAddressUserType extends AbstractStringUserType<InetAddress> {
    public InetAddressUserType() {
        super(InetAddress.class);
    }
}
