export interface JiraModel {
  key: string;
  title: string;
  type: string;
  status: string;
  assignee: string;
  reporter: string;
  parent: string;
  sub_task: string;
  affects_versions: string;
  fix_versions: string;
  sprint: string;
}
