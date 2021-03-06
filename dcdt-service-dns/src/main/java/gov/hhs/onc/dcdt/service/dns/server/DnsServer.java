package gov.hhs.onc.dcdt.service.dns.server;

import gov.hhs.onc.dcdt.dns.DnsTransportProtocol;
import gov.hhs.onc.dcdt.service.dns.config.DnsServerConfig;
import gov.hhs.onc.dcdt.service.server.ToolServer;

public interface DnsServer extends ToolServer<DnsTransportProtocol, DnsServerConfig> {
    public DnsServerTcpSocketListener getTcpSocketListener();

    public void setTcpSocketListener(DnsServerTcpSocketListener tcpSocketListener);

    public DnsServerUdpSocketListener getUdpSocketListener();

    public void setUdpSocketListener(DnsServerUdpSocketListener udpSocketListener);
}
