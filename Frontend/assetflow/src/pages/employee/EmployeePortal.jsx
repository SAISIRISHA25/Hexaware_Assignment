// ─── Employee Portal Router ───────────────────────────────────────────────────
import { EmployeeDashboard }  from "./EmployeeDashboard";
import { MyAssets }           from "./MyAssets";
import { RequestAsset }       from "./RequestAsset";
import { ServiceRequestsEmp } from "./ServiceRequestsEmp";
import { ReturnAsset }        from "./ReturnAsset";
import { AuditVerification }  from "./AuditVerification";
import { MyProfile }          from "./MyProfile";
import { EMP_NAV }            from "../../constants/navigation";

const PAGE_LABELS = Object.fromEntries(EMP_NAV.map((n) => [n.id, n.label]));

export function EmployeePortal({ activePage, user, token, onNav }) {
  const props = { user, token, onNav };

  switch (activePage) {
    case "emp-dashboard":      return <EmployeeDashboard  {...props} />;
    case "my-assets":          return <MyAssets           {...props} />;
    case "request-asset":      return <RequestAsset       {...props} />;
    case "emp-services":       return <ServiceRequestsEmp {...props} />;
    case "emp-returns":        return <ReturnAsset        {...props} />;
    case "audit-verification": return <AuditVerification  {...props} />;
    case "my-profile":         return <MyProfile          {...props} />;
    default:                   return <EmployeeDashboard  {...props} />;
  }
}

export function getEmployeePageTitle(activePage) {
  return PAGE_LABELS[activePage] || "Dashboard";
}
