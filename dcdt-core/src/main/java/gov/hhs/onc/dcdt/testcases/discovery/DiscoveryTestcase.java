package gov.hhs.onc.dcdt.testcases.discovery;

import gov.hhs.onc.dcdt.testcases.ToolTestcase;
import java.util.List;

public interface DiscoveryTestcase extends ToolTestcase<DiscoveryTestcaseResult, DiscoveryTestcaseDescription> {
    public List<DiscoveryTestcaseCertificate> getCertificates();

    public void setCertificates(List<DiscoveryTestcaseCertificate> certs);

    public DiscoveryTestcaseDescription getDiscoveryTestcaseDescription();

    public void setDiscoveryTestcaseDescription(DiscoveryTestcaseDescription discoveryTestcaseDescription);
}