import { Injectable } from '@angular/core';

export interface NavigationItem {
  id: string;
  title: string;
  type: 'item' | 'collapse' | 'group';
  icon?: string;
  url?: string;
  classes?: string;
  external?: boolean;
  target?: boolean;
  breadcrumbs?: boolean;
  children?: Navigation[];
}

export interface Navigation extends NavigationItem {
  children?: NavigationItem[];
}
const NavigationItems = [
  {
    id: 'dashboard',
    title: 'Dashboard',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'dashboard',
        title: 'Dashboard',
        type: 'item',
        classes: 'nav-item',
        url: '/admin/dashboard',
        icon: 'ti ti-dashboard',
        breadcrumbs: false
      }
    ]
  },
  {
    id: 'users',
    title: 'User management',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'crudUsers',
        title: 'Users',
        type: 'collapse',
        icon: 'ti ti-users-plus',
        children: [
          {
            id: 'addNew',
            title: 'Add New',
            type: 'item',
            url: 'users/addUser',
            breadcrumbs: false
          },
          {
            id: 'listUsers',
            title: 'List All',
            type: 'item',
            url: 'users/listUsers',
            breadcrumbs: false
          }
        ]
      }
    ]
  },
  {
    id: 'metaModel',
    title: 'MetaModel management',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'crudMetaModel',
        title: 'Meta-Models',
        type: 'collapse',
        icon: 'ti ti-brand-meta',
        children: [
          {
            id: 'addNew',
            title: 'Add New',
            type: 'item',
            url: 'metaModel/addMetaModel',
            breadcrumbs: false
          },
          {
            id: 'listMetaModels',
            title: 'List All',
            type: 'item',
            url: 'metaModel/listAll',
            breadcrumbs: false
          }
        ]
      }
    ]
  },
];

@Injectable()
export class NavigationItem {
  get() {
    return NavigationItems;
  }
}
