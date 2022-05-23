<template>
  <div>
    <el-container>
      <el-header> </el-header>
      <el-header> CS307 ProjectII </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
            <el-button type="primary" plain @click="backToHome">Home</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
          stockIn
          </el-col>
          <el-col :span="2">
            <input  type="file" id="files" ref="refFile1" v-on:change="importCsv1">
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
          placeOrder
          </el-col>
          <el-col :span="2">
            <input  type="file" id="files" ref="refFile2" v-on:change="importCsv2">
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
          updateOrder
          </el-col>
          <el-col :span="2">
            <input  type="file" id="files" ref="refFile3" v-on:change="importCsv3">
          </el-col>
        </el-row>
      </el-header>
      <el-header>
        <el-row>
          <el-col :span="2">
          deleteOrder
          </el-col>
          <el-col :span="2">
            <input  type="file" id="files" ref="refFile4" v-on:change="importCsv4">
          </el-col>
        </el-row>
      </el-header>
    </el-container>
  </div>
</template>

<script>
import Papa from 'papaparse'
export default {
  methods: {
    backToHome () {
      this.$router.push('Login')
    },
    importCsv1 () {
      let selectedFile = null
      selectedFile = this.$refs.refFile1.files[0]
      if (selectedFile === undefined) {
        return
      }
      var reader = new FileReader()
      reader.readAsDataURL(selectedFile)
      reader.onload = evt => {
        Papa.parse(selectedFile, {
          complete: res => {
            let data = res.data
            if (data[data.length - 1] === '') {
              data.pop()
            }
            for (var i = 0; i < data.length; i++) {
              let parts = data[i]
              this.$axios
                .post('/inventories', {
                  id: parseInt(parts[0]),
                  supplyCenter: parts[1],
                  productModel: parts[2],
                  supplyStaff: parts[3],
                  date: Date.parse(parts[4]),
                  purchasePrice: parseInt(parts[5]),
                  totalQuantity: parseInt(parts[6])
                })
            }
          }
        })
      }
    },
    importCsv2 () {
      let selectedFile = null
      selectedFile = this.$refs.refFile2.files[0]
      if (selectedFile === undefined) {
        return
      }
      var reader = new FileReader()
      reader.readAsDataURL(selectedFile)
      reader.onload = evt => {
        Papa.parse(selectedFile, {
          complete: res => {
            let data = res.data
            if (data[data.length - 1] === '') {
              data.pop()
            }
            let jsonList = []
            for (var i = 0; i < data.length; i++) {
              let parts = data[i]
              jsonList.push(
                {
                  contractNumber: parts[0],
                  enterprise: parts[1],
                  productModel: parts[2],
                  quantity: parseInt(parts[3]),
                  contractManager: parts[4],
                  contractDate: Date.parse(parts[5]),
                  estimatedDeliveryDate: Date.parse(parts[6]),
                  lodgementDate: Date.parse(parts[7]),
                  salesmanNumber: parts[8],
                  contractType: parts[9]
                }
              )
            }
            this.$axios.post('/orders', jsonList)
          }
        })
      }
    },
    importCsv3 () {
      let selectedFile = null
      selectedFile = this.$refs.refFile3.files[0]
      if (selectedFile === undefined) {
        return
      }
      var reader = new FileReader()
      reader.readAsDataURL(selectedFile)
      reader.onload = evt => {
        Papa.parse(selectedFile, {
          complete: res => {
            let data = res.data
            if (data[data.length - 1] === '') {
              data.pop()
            }
            let jsonList = []
            for (var i = 0; i < data.length; i++) {
              let parts = data[i]
              jsonList.push(
                {
                  contractNumber: parts[0],
                  enterprise: '',
                  productModel: parts[1],
                  quantity: parseInt(parts[3]),
                  contractManager: '',
                  contractDate: '',
                  estimatedDeliveryDate: Date.parse(parts[4]),
                  lodgementDate: Date.parse(parts[5]),
                  salesmanNumber: parts[2],
                  contractType: ''
                }
              )
            }
            console.log('/orders', jsonList)
            this.$axios.put('/orders', jsonList)
          }
        })
      }
    },
    importCsv4 () {
      let selectedFile = null
      selectedFile = this.$refs.refFile4.files[0]
      if (selectedFile === undefined) {
        return
      }
      var reader = new FileReader()
      reader.readAsDataURL(selectedFile)
      reader.onload = evt => {
        Papa.parse(selectedFile, {
          complete: res => {
            let data = res.data
            if (data[data.length - 1] === '') {
              data.pop()
            }
            // let jsonList = []
            for (var i = 1; i < data.length - 1; i++) {
              let parts = data[i]
              console.log('/orders?' +
              'contract=' + parts[0] +
              '&salesman=' + parts[1] +
              '&seq=' + parts[2])
              this.$axios.delete('/orders?' +
              'contract=' + parts[0] +
              '&salesman=' + parts[1] +
              '&seq=' + parts[2])
              // jsonList.push(
              //   {
              //     contract: parts[0],
              //     salesman: parts[1],
              //     seq: parseInt(parts[2])
              //   }
              // )
            }
            // console.log(jsonList)
            // this.$axios.delete('/orders', jsonList)
          }
        })
      }
    }
  }
}
</script>

<style>
.el-header {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 400px;
}

body > .el-container {
  margin-bottom: 40px;
}
</style>
