const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const webpack = require('webpack');

module.exports = {
    mode: 'development',
    entry: {
        panel: './src/panel/panel.js',
        index: './src/main/index.js',
        login: './src/login/login.js',
    },
    output: {
        filename: 'scripts/[name].js',
        path: path.resolve(__dirname, '../static/'),
        assetModuleFilename: 'assets/[name][ext]'
    },
    resolve: {
        alias: {
            jQuery: path.resolve(__dirname, 'node_modules/jquery')
        }
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: [
                    {
                        loader: MiniCssExtractPlugin.loader,
                        options: {
                            publicPath: "../"
                        },
                    },
                    {
                        loader: 'css-loader',
                        options: {
                            sourceMap: true
                        }
                    }
                ],
            },
        ],
    },
    optimization: {
        minimizer: [
            `...`,
            new CssMinimizerPlugin()
        ]
    }
    ,
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        }),
        new MiniCssExtractPlugin({
            filename: 'styles/[name].css'
        })
    ]
}
