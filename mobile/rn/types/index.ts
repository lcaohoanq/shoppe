export type Profile = {
  name: string;
  avatarUrl: string;
  githubAccount: string;
  role: string;
  socialLinks: {
    type: string;
    url: string;
    iconUrl: string;
  }[];
};
