package gov.hhs.onc.dcdt.dns.utils;

import gov.hhs.onc.dcdt.dns.DnsException;
import gov.hhs.onc.dcdt.dns.DnsResultType;
import gov.hhs.onc.dcdt.net.utils.ToolInetAddressUtils;
import gov.hhs.onc.dcdt.utils.ToolArrayUtils;
import gov.hhs.onc.dcdt.utils.ToolCollectionUtils;
import gov.hhs.onc.dcdt.utils.ToolStringUtils;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;

public abstract class ToolDnsResolverUtils {
    public static class ResolverSocketAddressTransformer implements Transformer<InetAddress, InetSocketAddress> {
        public final static ResolverSocketAddressTransformer INSTANCE = new ResolverSocketAddressTransformer();

        @Override
        public InetSocketAddress transform(InetAddress addr) {
            return new InetSocketAddress(addr, SimpleResolver.DEFAULT_PORT);
        }
    }

    public static Resolver fromAddresses(@Nullable InetAddress ... addrs) throws DnsException {
        return fromAddresses(ToolArrayUtils.asList(addrs));
    }

    public static Resolver fromAddresses(@Nullable Iterable<InetAddress> addrs) throws DnsException {
        return fromSocketAddresses(CollectionUtils.collect(addrs, ResolverSocketAddressTransformer.INSTANCE));
    }

    public static Resolver fromSocketAddresses(@Nullable InetSocketAddress ... socketAddrs) throws DnsException {
        return fromSocketAddresses(ToolArrayUtils.asList(socketAddrs));
    }

    public static Resolver fromSocketAddresses(@Nullable Iterable<InetSocketAddress> socketAddrs) throws DnsException {
        List<Resolver> resolvers = new ArrayList<>();

        if (socketAddrs != null) {
            for (InetSocketAddress socketAddr : socketAddrs) {
                resolvers.add(fromSocketAddress(socketAddr));
            }
        }

        try {
            return !CollectionUtils.isEmpty(resolvers) ? new ExtendedResolver(ToolCollectionUtils.toArray(resolvers, Resolver.class)) : new ExtendedResolver();
        } catch (UnknownHostException e) {
            throw new DnsException(String.format("Unable to create composite DNS resolver (resolvers=[%s]).", ToolStringUtils.joinDelimit(resolvers, ", ")), e);
        }
    }

    public static Resolver fromSocketAddress(InetSocketAddress socketAddr) throws DnsException {
        InetAddress socketConnAddr = ToolInetAddressUtils.getConnectionAddress(socketAddr.getAddress());

        try {
            SimpleResolver resolver = new SimpleResolver(socketConnAddr.getHostAddress());
            resolver.setPort(socketAddr.getPort());

            return resolver;
        } catch (UnknownHostException e) {
            throw new DnsException(String.format("Unable to create DNS resolver (socketConnAddr=%s, socketPort=%d).", socketConnAddr.getHostAddress(),
                socketAddr.getPort()), e);
        }
    }

    public static DnsResultType findResultType(int result) throws DnsException {
        for (DnsResultType enumItem : EnumSet.allOf(DnsResultType.class)) {
            if (enumItem.getResult() == result) {
                return enumItem;
            }
        }

        throw new DnsException(String.format("Unknown DNS result type (result=%d).", result));
    }
}
