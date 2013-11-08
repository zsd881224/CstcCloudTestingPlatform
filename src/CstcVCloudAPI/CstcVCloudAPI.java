package CstcVCloudAPI;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

import com.vmware.vcloud.api.rest.schema.InstantiateVAppTemplateParamsType;
import com.vmware.vcloud.api.rest.schema.InstantiationParamsType;
import com.vmware.vcloud.api.rest.schema.ReferenceType;
import com.vmware.vcloud.sdk.Organization;
import com.vmware.vcloud.sdk.VM;
import com.vmware.vcloud.sdk.Vapp;
import com.vmware.vcloud.sdk.VappTemplate;
import com.vmware.vcloud.sdk.VcloudClient;
import com.vmware.vcloud.sdk.Vdc;
import com.vmware.vcloud.sdk.VirtualDisk;
import com.vmware.vcloud.sdk.constants.UndeployPowerActionType;
import com.vmware.vcloud.sdk.constants.VMStatus;
import com.vmware.vcloud.sdk.constants.Version;

import Constant.Constant;

public class CstcVCloudAPI {
	public static boolean deleteVapp(String orgName, String vdcName,
			String vappName) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			Organization organization = Organization
					.getOrganizationByReference(vcloudClient,
							vcloudClient.getOrgRefByName(orgName));
			;
			Vdc vdc = Vdc.getVdcByReference(vcloudClient,
					organization.getVdcRefByName(vdcName));
			Vapp vapp = Vapp.getVappByReference(vcloudClient,
					vdc.getVappRefByName(vappName));
			vapp.delete();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static Collection<VM> listVM(String orgName, String vdcName,
			String vappName) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		Collection<VM> vmsCollection;
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			Organization organization = Organization
					.getOrganizationByReference(vcloudClient,
							vcloudClient.getOrgRefByName(orgName));
			Vdc vdc = Vdc.getVdcByReference(vcloudClient,
					organization.getVdcRefByName(vdcName));
			Vapp vapp = Vapp.getVappByReference(vcloudClient,
					vdc.getVappRefByName(vappName));
			vmsCollection = vapp.getChildrenVms();
		} catch (Exception e) {
			return null;
		}

		return vmsCollection;
	}

	public static String getVmParent(String vmid) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		String vappName;
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			VM vm = VM.getVMById(vcloudClient, vmid);
			Vapp vapp = Vapp.getVappByReference(vcloudClient,
					vm.getParentVappReference());
			vappName = vapp.getReference().getName();
		} catch (Exception e) {
			return null;
		}

		return vappName;
	}

	public static boolean sendOrder(String vmid, String order) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			VM vm = VM.getVMById(vcloudClient, vmid);
			if (vm.getVMStatus() == VMStatus.valueOf("POWERED_OFF")
					&& order.equals("poweron"))
				vm.powerOn().waitForTask(0);
			else if (vm.getVMStatus() == VMStatus.valueOf("SUSPENDED")
					&& order.equals("resume"))
				vm.powerOn().waitForTask(0);
			else if (vm.getVMStatus() == VMStatus.valueOf("POWERED_ON")
					&& order.equals("suspend"))
				vm.suspend().waitForTask(0);
			else if (vm.getVMStatus() == VMStatus.valueOf("POWERED_ON")
					&& order.equals("poweroff")) {
				vm.powerOff().waitForTask(0);
				vm.undeploy(UndeployPowerActionType.FORCE).waitForTask(0);
			} else if (vm.getVMStatus() == VMStatus.valueOf("POWERED_ON")
					&& order.equals("reset"))
				vm.reset().waitForTask(0);
			else
				return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean getVMDetail(String vmid,
			HashMap<String, String> result) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			VM vm = VM.getVMById(vcloudClient, vmid);
			result.put("vmname", vm.getResource().getName());
			result.put("computername", vm.getGuestCustomizationSection()
					.getComputerName());
			result.put("noofcpu", String.valueOf(vm.getCpu().getNoOfCpus()));
			result.put("memorysize", vm.getMemory().getMemorySize() + " Mb");
			int disk_count = 1;
			for (VirtualDisk disk : vm.getDisks())
				if (disk.isHardDisk())
					result.put("harddisk" + disk_count++,
							disk.getHardDiskSize() + " Mb");
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean createVapp(String orgName, String vdcName,
			String templateName, String vappName) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			Organization organization = Organization
					.getOrganizationByReference(vcloudClient,
							vcloudClient.getOrgRefByName(orgName));
			Vdc vdc = Vdc.getVdcByReference(vcloudClient,
					organization.getVdcRefByName(vdcName));
			
			Collection<ReferenceType> vapptc = vdc
					.getVappTemplateRefsByName(templateName);
			if (vapptc.isEmpty()) return false;
			
			VappTemplate vappTemplate = VappTemplate.getVappTemplateByReference(
					vcloudClient, vapptc.iterator().next());
			
			InstantiationParamsType instantiationParams = new InstantiationParamsType();
			
			InstantiateVAppTemplateParamsType instVappTemplParams = new InstantiateVAppTemplateParamsType();
			instVappTemplParams.setName(vappName);
			instVappTemplParams.setSource(vappTemplate.getReference());
			instVappTemplParams.setInstantiationParams(instantiationParams);
			
			vdc.instantiateVappTemplate(instVappTemplParams);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	public static String acquireTicket(String vmid) {
		VcloudClient.setLogLevel(Level.OFF);
		VcloudClient vcloudClient = new VcloudClient(Constant.vServer,
				Version.V5_1);
		String Ticket = null;
		try {
			vcloudClient.registerScheme("https", 443,
					FakeSSLSocketFactory.getInstance());
			vcloudClient.login(Constant.vUser, Constant.vPswd);
			VM vm = VM.getVMById(vcloudClient, vmid);
			Ticket = vm.acquireTicket().getValue();
		} catch (Exception e) {
			return null;
		}
		
		return Ticket;
	}
}