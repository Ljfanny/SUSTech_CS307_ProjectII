import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Center from '@/components/Center'
import Contract from '@/components/Contract'
import Enterprise from '@/components/Enterprise'
import Inventory from '@/components/Inventory'
import Model from '@/components/Model'
import Order from '@/components/Order'
import Staff from '@/components/Staff'
import Yjsc from '@/components/Yjsc'
import Import from '@/components/Import'
import StaffCount from '@/components/StaffCount'
import OrderCount from '@/components/OrderCount'
import ContractCount from '@/components/ContractCount'
import Favorite from '@/components/Favorite'
import Average from '@/components/Average'
import NeverSold from '@/components/NeverSold'
import Product from '@/components/Product'
import ContractInfo from '@/components/ContractInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/center',
      name: 'Center',
      component: Center
    },
    {
      path: '/contract',
      name: 'Contract',
      component: Contract
    },
    {
      path: '/enterprise',
      name: 'Enterprise',
      component: Enterprise
    },
    {
      path: '/inventory',
      name: 'Inventory',
      component: Inventory
    },
    {
      path: '/model',
      name: 'Model',
      component: Model
    },
    {
      path: '/order',
      name: 'Order',
      component: Order
    },
    {
      path: '/staff',
      name: 'Staff',
      component: Staff
    },
    {
      path: '/yjsc',
      name: 'Yjsc',
      component: Yjsc
    },
    {
      path: '/import',
      name: 'Import',
      component: Import
    },
    {
      path: '/staffCount',
      name: 'StaffCount',
      component: StaffCount
    },
    {
      path: '/contractCount',
      name: 'ContractCount',
      component: ContractCount
    },
    {
      path: '/orderCount',
      name: 'OrderCount',
      component: OrderCount
    },
    {
      path: '/neverSold',
      name: 'NeverSold',
      component: NeverSold
    },
    {
      path: '/average',
      name: 'Average',
      component: Average
    },
    {
      path: '/favorite',
      name: 'Favorite',
      component: Favorite
    },
    {
      path: '/product',
      name: 'Product',
      component: Product
    },
    {
      path: '/contractInfo',
      name: 'ContractInfo',
      component: ContractInfo
    }
  ]
})
